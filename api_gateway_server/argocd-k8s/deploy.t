apiVersion: apps/v1
kind: Deployment
metadata:
  name: ${USER_NAME}-${SERVICE_NAME}
  namespace: ${NAMESPACE}
spec:
  replicas: ${REPLICAS}
  selector:
    matchLabels:
      app: ${USER_NAME}-${SERVICE_NAME}
  template:
    metadata:
      labels:
        app: ${USER_NAME}-${SERVICE_NAME}
    spec:
      serviceAccountName: default
      containers:
      - name: ${IMAGE_NAME}
        image: ${DOCKER_REGISTRY}/${USER_NAME}-${IMAGE_NAME}:${VERSION}
        imagePullPolicy: Always
        env:
        - name: USER_NAME
          value: ${USER_NAME}
        - name: NAMESPACE
          value: ${NAMESPACE}
        - name: USER_URI
          valueFrom:
            configMapKeyRef:
              name: gateway-config
              key: USER_URI
        - name: ORDER_URI
          valueFrom:
            configMapKeyRef:
              name: gateway-config
              key: ORDER_URI
        - name: ITEM_URI
          valueFrom:
            configMapKeyRef:
              name: gateway-config
              key: ITEM_URI
        ports:
        - containerPort: 19901
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 19901
          initialDelaySeconds: 5
          periodSeconds: 3
          failureThreshold: 2
        readinessProbe:
          httpGet:
            path: /actuator/ready
            port: 19901
          initialDelaySeconds: 5
          periodSeconds: 3
          failureThreshold: 2