# Dockerfile 방식으로 SQL 초기화 수행 (ConfigMap 제거됨)
---
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: ${USER_NAME}-${SERVICE_NAME}
  namespace: ${NAMESPACE}
spec:
  serviceName: ${USER_NAME}-${SERVICE_NAME}
  replicas: 1
  selector:
    matchLabels:
      app: ${USER_NAME}-${SERVICE_NAME}
  template:
    metadata:
      labels:
        app: ${USER_NAME}-${SERVICE_NAME}
    spec:
      containers:
        - name: order-db
          image: ${DOCKER_REGISTRY}/${USER_NAME}-my-orderdb:${VERSION}
          ports:
            - containerPort: 3306
          env:
            - name: MYSQL_ROOT_PASSWORD
              value: ${ROOT_PASSWORD}
            - name: MYSQL_DATABASE
              value: ${ORDER_DATABASE}
            - name: MYSQL_USER
              value: ${USER}
            - name: MYSQL_PASSWORD
              value: ${PASSWORD}
            - name: MYSQL_TCP_PORT
              value: "3306"


        - name: item-db
          image: ${DOCKER_REGISTRY}/${USER_NAME}-my-itemdb:${VERSION}
          ports:
            - containerPort: 3307
          env:
            - name: MYSQL_ROOT_PASSWORD
              value: ${ROOT_PASSWORD}
            - name: MYSQL_DATABASE
              value: ${ITEM_DATABASE}
            - name: MYSQL_USER
              value: ${USER}
            - name: MYSQL_PASSWORD
              value: ${PASSWORD}
            - name: MYSQL_TCP_PORT
              value: "3307"


        - name: user-db
          image: ${DOCKER_REGISTRY}/${USER_NAME}-my-userdb:${VERSION}
          ports:
            - containerPort: 3308
          env:
            - name: MYSQL_ROOT_PASSWORD
              value: ${ROOT_PASSWORD}
            - name: MYSQL_DATABASE
              value: ${USER_DATABASE}
            - name: MYSQL_USER
              value: ${USER}
            - name: MYSQL_PASSWORD
              value: ${PASSWORD}
            - name: MYSQL_TCP_PORT
              value: "3308"

---
apiVersion: v1
kind: Service
metadata:
  name: ${USER_NAME}-order-db
  namespace: ${NAMESPACE}
spec:
  selector:
    app: ${USER_NAME}-${SERVICE_NAME}
  ports:
    - port: 3306
      targetPort: 3306
      name: order
---
apiVersion: v1
kind: Service
metadata:
  name: ${USER_NAME}-item-db
  namespace: ${NAMESPACE}
spec:
  selector:
    app: ${USER_NAME}-${SERVICE_NAME}
  ports:
    - port: 3307
      targetPort: 3307
      name: item
---
apiVersion: v1
kind: Service
metadata:
  name: ${USER_NAME}-user-db
  namespace: ${NAMESPACE}
spec:
  selector:
    app: ${USER_NAME}-${SERVICE_NAME}
  ports:
    - port: 3308
      targetPort: 3308
      name: user
