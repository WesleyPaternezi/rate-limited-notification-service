CREATE TABLE IF NOT EXISTS notification(
    id SERIAL primary key,
    user_id varchar(255) not null,
    notification_type varchar(255) not null,
    content_body varchar(255) null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notification_user_id ON notification(user_id, notification_type);
CREATE INDEX idx_notification_created_at ON notification(created_at);
CREATE INDEX idx_notification_notification_type ON notification(notification_type);