FROM nginx

COPY /nginx/default.conf /etc/nginx/conf.d/

RUN rm -rf /usr/share/nginx/html/*

COPY /dist/go2shop-app /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]