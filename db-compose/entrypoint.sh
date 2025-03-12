#!/bin/sh
export $(grep -v '^#' .env | xargs)
for file in user-init-template.sql item-init-template.sql order-init-template.sql; do
  envsubst < "$file" > "${file/-template/}"
done