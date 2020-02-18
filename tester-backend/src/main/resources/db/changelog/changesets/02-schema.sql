CREATE TABLE IF NOT EXISTS bugs
(
  id            BIGSERIAL         NOT NULL,
  device_id     BIGINT            NOT NULL,
  tester_id     BIGINT            NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS devices
(
  id            BIGSERIAL         NOT NULL,
  description   VARCHAR (255)     NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS testers
(
  id            BIGSERIAL         NOT NULL,
  country       VARCHAR (255)     NOT NULL,
  first_name    VARCHAR (255)     NOT NULL,
  last_login    TIMESTAMP         NOT NULL,
  last_name     VARCHAR (255)     NOT NULL,
  PRIMARY KEY (id)
);

create table testers_devices
(
  tester_id     BIGINT            NOT NULL,
  device_id     BIGINT            NOT NULL,
  primary key (tester_id, device_id)
);

ALTER TABLE bugs ADD CONSTRAINT FK9moo37hd1npqlpionl03t1vui FOREIGN KEY (device_id) REFERENCES devices;
ALTER TABLE bugs ADD CONSTRAINT FKebig9gnnfdgf3e086cgtp8dw1 FOREIGN KEY (tester_id) REFERENCES testers;

ALTER TABLE testers_devices ADD CONSTRAINT FKjo0x9oqi9ljvqtoccxcn71phq FOREIGN KEY (tester_id) REFERENCES testers;
ALTER TABLE testers_devices ADD CONSTRAINT FK9thjp3ah4nv35rrmsd3k562fw FOREIGN KEY (device_id) REFERENCES devices;