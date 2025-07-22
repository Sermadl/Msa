FROM mariadb:latest
COPY ../my.cnf /etc/mysql/conf.d/
COPY user-init.sql /docker-entrypoint-initdb.d/init.sql
RUN chmod 644 /docker-entrypoint-initdb.d/init.sql