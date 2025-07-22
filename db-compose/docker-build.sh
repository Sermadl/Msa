#!/bin/bash
NAME=sk067
VERSION="1.1.0"

sudo docker buildx build \
  --platform linux/amd64 \
  --no-cache \
  -t ${NAME}-my-userdb:${VERSION} --push ./user

sudo docker buildx build \
  --platform linux/amd64 \
  --no-cache \
  -t ${NAME}-my-orderdb:${VERSION} --push ./order

sudo docker buildx build \
  --platform linux/amd64 \
  --no-cache \
  -t ${NAME}-my-itemdb:${VERSION} --push ./item