apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  annotations:
    cert-manager.io/cluster-issuer: letsencrypt-prod
  name: ${USER_NAME}-mypang-ingress
  namespace: ${NAMESPACE}
spec:
  ingressClassName: public-nginx
  rules:
  - host: ${USER_NAME}-mypang
    http:
      paths:
      - backend:
          service:
            name: ${USER_NAME}-${SERVICE_NAME}
            port:
              number: 19901
        path: /
        pathType: Prefix
  tls:
  - hosts:
    - ${USER_NAME}-mypang
    secretName: ${USER_NAME}-mypang-tls-secret
