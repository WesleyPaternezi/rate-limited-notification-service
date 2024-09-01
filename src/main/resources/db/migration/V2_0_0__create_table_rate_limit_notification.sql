CREATE TABLE IF NOT EXISTS notification_config(
    id SERIAL primary key,
    notification_type varchar(255) not null,
    rate_limit_interval INT null,
    rate_limit_count INT null
);

CREATE INDEX idx_notification_config_notification_type ON notification_config(notification_type);