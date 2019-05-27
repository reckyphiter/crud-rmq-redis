# crud-rmq-redis
RESTful CRUD application built using Java, RabbitMQ, PostgreSQL and Redis

This application consists of three modules:
1. crud-common is library that contains shared utils used by other modules.
2. crud-proxy is RESTful service that receives requests and sends data to message queue.
3. crud-worker is service that consumes data from message queue and stores data to database.

This application using RabbitMQ Delayed Message Exchange Plugin to set delay for messages scheduling.
You can get detailed information about this plugin on this link:
https://www.rabbitmq.com/blog/2015/04/16/scheduling-messages-with-rabbitmq/
