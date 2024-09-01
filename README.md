# rate-limited-notification-service

Notification system that sends out email notifications of various types (status update, daily news, project invitations, etc)


## Badges

[![Kotlin Language](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org/)
[![Gradle Automation](https://img.shields.io/badge/Tool-Gradle-darkgreen.svg)](http://www.gnu.org/licenses/agpl-3.0)
[![SpringBoot Framework](https://img.shields.io/badge/Framework-SpringBoot-green.svg)](https://opensource.org/licenses/)
[![Postgres Database](https://img.shields.io/badge/Database-Postgres-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)
[![Flyway Migration](https://img.shields.io/badge/Migration-Flyway-red.svg)](http://www.gnu.org/licenses/agpl-3.0)
[![Docker Container](https://img.shields.io/badge/Container-Docker-lightblue.svg)](http://www.gnu.org/licenses/agpl-3.0)


## API Reference

#### Create new notification

```http
  POST /notification
```
| Parameter | Type     | Required                |
| :-------- | :------- | :------------------------- |
| `user_id` | `string` | **yes**|
| `notification_type` | `string` |**yes**|
| `content_body` | `string` |**no**|


#### Create new notification-config

```http
  POST /notification-config
```
| Parameter | Type     | Required                       |
| :-------- | :------- | :-------------------------------- |
| `notification_type` | `string` | **yes**|
| `rate_limit_interval` | `string` | **yes**|
| `rate_limit_count` | `string` | **yes**|

#### Get new notification-config
```http
  GET /notification-config/{notification-config}

```

#### Update notification-config fields
```http
  PUT /notification-config
```

| Parameter | Type     | Required                       |
| :-------- | :------- | :-------------------------------- |
| `notification_type` | `string` | **yes**|
| `rate_limit_interval` | `string` | **yes**|
| `rate_limit_count` | `string` | **yes**|


#### Delete a notification-config
```http
  DELETE /notification-config/{notification-config}
```
