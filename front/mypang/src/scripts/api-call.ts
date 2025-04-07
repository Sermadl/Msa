export const Response = {
  SUCCESS: 0,
  FAIL: 1,
};

const apiHeaders = {
  "Content-Type": "application/json",
};

const handleResponse = async (response: Response) => {
  const status = response.status;
  const data = await response.json();

  if (response.ok) {
    return {
      result: Response.SUCCESS,
      code: 200,
      message: "",
      data: data,
    };
  } else {
    const errorMsg = data.message || "오류가 발생했습니다.";
    return {
      result: Response.FAIL,
      code: data.code,
      status: status,
      message: errorMsg,
      data: null,
    };
  }
};

const handleError = (error: any) => {
  const errorMsg = error.message || error || "Unknown error";
  return {
    result: Response.FAIL,
    code: "NETWORK_ERROR",
    message: errorMsg,
    data: null,
  };
};

const sendRequest = async (
  method: string,
  url: string,
  headers: { [key: string]: any } | null = {},
  body: any = null
) => {
  try {
    const options: RequestInit = {
      method,
      headers: { ...apiHeaders, ...headers },
    };

    if (method !== "GET" && method !== "HEAD") {
      options.body = JSON.stringify(body);
    }

    const response = await fetch(url, options);
    return await handleResponse(response);
  } catch (error: any) {
    return handleError(error);
  }
};

const buildUrlWithParams = (
  url: string,
  params: { [key: string]: any } | null
) => {
  if (!params) return url;
  const searchParams = new URLSearchParams();
  Object.entries(params).forEach(([key, value]) => {
    if (Array.isArray(value)) {
      value.forEach((val) => searchParams.append(key, val));
    } else {
      searchParams.append(key, value);
    }
  });
  return `${url}?${searchParams.toString()}`;
};

const apiCall = {
  Response,

  get: async (url: string, headers: any = null, queryParams: any = null) =>
    await sendRequest("GET", buildUrlWithParams(url, queryParams), headers),

  post: async (url: string, headers: any = null, body: any = null) =>
    await sendRequest("POST", url, headers, body),

  put: async (url: string, headers: any = null, body: any = null) =>
    await sendRequest("PUT", url, headers, body),

  delete: async (url: string, headers: any = null, body: any = null) =>
    await sendRequest("DELETE", url, headers, body),
};

export default apiCall;
