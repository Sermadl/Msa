---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ${USER_NAME}-${SERVICE_NAME}
  namespace: ${NAMESPACE}
spec:
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
        - name: kafka-broker-0
          image: bitnami/kafka:3.5.1-debian-11-r44
          ports:
            - containerPort: 9092
            - containerPort: 9093
            - containerPort: 9094
          env:
            - name: KAFKA_CFG_BROKER_ID
              value: "0"
            - name: KAFKA_CFG_NODE_ID
              value: "0"
            - name: KAFKA_KRAFT_CLUSTER_ID
              value: HsDBs9l6UUmQq7Y5E6bNlw
            - name: KAFKA_CFG_CONTROLLER_QUORUM_VOTERS
              value: "0@localhost:9093,1@localhost:19093,2@localhost:29093"
            - name: ALLOW_PLAINTEXT_LISTENER
              value: "yes"
            - name: KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE
              value: "true"
            - name: KAFKA_CFG_LISTENERS
              value: "PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094"
            - name: KAFKA_CFG_ADVERTISED_LISTENERS
              value: "PLAINTEXT://localhost:9092,EXTERNAL://${USER_NAME}-${SERVICE_NAME}:9094"
            - name: KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP
              value: "CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT"
            - name: KAFKA_CFG_OFFSETS_TOPIC_REPLICATION_FACTOR
              value: "1"
            - name: KAFKA_CFG_TRANSACTION_STATE_LOG_REPLICATION_FACTOR
              value: "1"
            - name: KAFKA_CFG_TRANSACTION_STATE_LOG_MIN_ISR
              value: "1"
            - name: KAFKA_CFG_PROCESS_ROLES
              value: "controller,broker"
            - name: KAFKA_CFG_CONTROLLER_LISTENER_NAMES
              value: "CONTROLLER"
        - name: kafka-broker-1
          image: bitnami/kafka:3.5.1-debian-11-r44
          ports:
            - containerPort: 19092
            - containerPort: 19093
            - containerPort: 19094
          env:
            - name: KAFKA_CFG_BROKER_ID
              value: "1"
            - name: KAFKA_CFG_NODE_ID
              value: "1"
            - name: KAFKA_KRAFT_CLUSTER_ID
              value: HsDBs9l6UUmQq7Y5E6bNlw
            - name: KAFKA_CFG_CONTROLLER_QUORUM_VOTERS
              value: "0@localhost:9093,1@localhost:19093,2@localhost:29093"
            - name: ALLOW_PLAINTEXT_LISTENER
              value: "yes"
            - name: KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE
              value: "true"
            - name: KAFKA_CFG_LISTENERS
              value: "PLAINTEXT://:19092,CONTROLLER://:19093,EXTERNAL://:19094"
            - name: KAFKA_CFG_ADVERTISED_LISTENERS
              value: "PLAINTEXT://localhost:19092,EXTERNAL://${USER_NAME}-${SERVICE_NAME}:19094"
            - name: KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP
              value: "CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT"
            - name: KAFKA_CFG_OFFSETS_TOPIC_REPLICATION_FACTOR
              value: "1"
            - name: KAFKA_CFG_TRANSACTION_STATE_LOG_REPLICATION_FACTOR
              value: "1"
            - name: KAFKA_CFG_TRANSACTION_STATE_LOG_MIN_ISR
              value: "1"
            - name: KAFKA_CFG_PROCESS_ROLES
              value: "controller,broker"
            - name: KAFKA_CFG_CONTROLLER_LISTENER_NAMES
              value: "CONTROLLER"
        - name: kafka-broker-2
          image: bitnami/kafka:3.5.1-debian-11-r44
          ports:
            - containerPort: 29092
            - containerPort: 29093
            - containerPort: 29094
          env:
            - name: KAFKA_CFG_BROKER_ID
              value: "2"
            - name: KAFKA_CFG_NODE_ID
              value: "2"
            - name: KAFKA_KRAFT_CLUSTER_ID
              value: HsDBs9l6UUmQq7Y5E6bNlw
            - name: KAFKA_CFG_CONTROLLER_QUORUM_VOTERS
              value: "0@localhost:9093,1@localhost:19093,2@localhost:29093"
            - name: ALLOW_PLAINTEXT_LISTENER
              value: "yes"
            - name: KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE
              value: "true"
            - name: KAFKA_CFG_LISTENERS
              value: "PLAINTEXT://:29092,CONTROLLER://:29093,EXTERNAL://:29094"
            - name: KAFKA_CFG_ADVERTISED_LISTENERS
              value: "PLAINTEXT://localhost:29092,EXTERNAL://${USER_NAME}-${SERVICE_NAME}:29094"
            - name: KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP
              value: "CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT"
            - name: KAFKA_CFG_OFFSETS_TOPIC_REPLICATION_FACTOR
              value: "1"
            - name: KAFKA_CFG_TRANSACTION_STATE_LOG_REPLICATION_FACTOR
              value: "1"
            - name: KAFKA_CFG_TRANSACTION_STATE_LOG_MIN_ISR
              value: "1"
            - name: KAFKA_CFG_PROCESS_ROLES
              value: "controller,broker"
            - name: KAFKA_CFG_CONTROLLER_LISTENER_NAMES
              value: "CONTROLLER"
---
apiVersion: v1
kind: Service
metadata:
  name: ${USER_NAME}-${SERVICE_NAME}
  namespace: ${NAMESPACE}
spec:
  selector:
    app: ${USER_NAME}-${SERVICE_NAME}
  ports:
    - name: kafka-0
      port: 9094
      targetPort: 9094
    - name: kafka-1
      port: 19094
      targetPort: 19094
    - name: kafka-2
      port: 29094
      targetPort: 29094
