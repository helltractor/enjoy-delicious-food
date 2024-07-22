# 问题总结

## nginx挂载文件

```docker-compose.yml
volumes:
  - ./nginx.conf:/etc/nginx/nginx.conf          // 挂载nginx配置文件
  - ./nginx/conf.d:/etc/nginx/conf.d            // 挂载nginx配置文件
  - ./docker/nginx-logs:/var/log/nginx          // 挂载nginx日志文件
  - ./docker/nginx-html:/usr/share/nginx/html   // 挂载nginx静态文件
```

## nginx配置文件

```nginx.conf
user  nginx;
worker_processes  auto;

error_log  /var/log/nginx/error.log notice;
pid        /var/run/nginx.pid;

events {
    worker_connections  1024;
}

http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    ···

	upstream webservers{
	  server host.docker.internal:8080 weight=90;
	}

    server {
        listen       80;
        server_name  localhost;

        location / {
            root   /usr/share/nginx/html/enjoy;
            index  index.html index.htm;
        }

        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   /usr/share/nginx/html;
        }

        # 反向代理,处理管理端发送的请求
        location /api/ {
			proxy_pass   http://host.docker.internal:8080/admin/;
        }
        
        ···
    }
}
```
> 由于使用的时 docker 中的 nginx，而 SpringBoot 项目是在本地运行的，所以需要使用`host.docker.internal`来访问本地的 SpringBoot 项目  
> 可以将 SpringBoot 项目打包成镜像，然后使用 docker 运行，这样可以使用`localhost`来 SpringBoot 项目了
> root 指定容器静态文件的路径，index 指定默认访问的文件

## mysql挂载文件

```docker-compose.yml
volumes:
  - ./mysql/schema.sql:/docker-entrypoint-initdb.d/schema.sql   // 挂载mysql初始化sql文件
  - ./mysql/conf/my.cnf:/etc/mysql/my.cnf   // 挂载mysql配置文件
  - ./docker/mysql-data:/var/lib/mysql  // 挂载mysql数据文件
  - ./docker/mysql-logs:/var/log/mysql  // 挂载mysql日志文件
privileged: true
entrypoint: bash -c "chmod 644 /etc/mysql/my.cnf && exec /usr/local/bin/docker-entrypoint.sh mysqld"
```
> 注意需要更改读写权限，mysql才能读取到配置文件

## mysql配置文件

```my.cnf
[client]
default-character-set = utf8mb4

[mysql]
default-character-set = utf8mb4

[mysqld]
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci

[mysqldump]
default-character-set = utf8mb4
```
> mysql的配置文件，设置mysql的字符集为utf8mb4，这样可以支持中文字符，避免乱码问题
