CREATE SCHEMA IF NOT EXISTS mrschema;
SET search_path TO mrschema;

CREATE TABLE "user"
(
    id        BIGSERIAL PRIMARY KEY,
    version   BIGINT NOT NULL,
    login     VARCHAR(255) UNIQUE NOT NULL,
    password  VARCHAR(255) NOT NULL,
    name      VARCHAR(255) NOT NULL,
    role      VARCHAR(10)
);

CREATE TABLE doctor
(
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    user_id BIGINT REFERENCES "user" (id)
);

CREATE TABLE patient
(
    id               BIGSERIAL PRIMARY KEY,
    version          BIGINT NOT NULL,
    diagnosis        VARCHAR(255),
    insurance_number VARCHAR(14) UNIQUE NOT NULL,
    name             VARCHAR(255) NOT NULL,
    patient_status   VARCHAR(255) NOT NULL,
    doctor_id        BIGINT REFERENCES doctor (id)
);

CREATE TABLE treatment
(
    id      BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL,
    name    VARCHAR(255) NOT NULL,
    type    VARCHAR(255) NOT NULL,
    deleted BOOLEAN NOT NULL
);

CREATE TABLE time_pattern
(
    id              BIGSERIAL PRIMARY KEY,
    version         BIGINT NOT NULL,
    daily_frequency INTEGER NOT NULL,
    time_basis      VARCHAR(255) NOT NULL
);

CREATE TABLE time_pattern_element
(
    id              BIGSERIAL PRIMARY KEY,
    version         BIGINT NOT NULL,
    day_of_week     VARCHAR(255),
    time_pattern_id BIGINT REFERENCES time_pattern (id)
);

CREATE TABLE prescription
(
    id                  BIGSERIAL PRIMARY KEY,
    version             BIGINT NOT NULL,
    uuid                UUID NOT NULL,
    dosage              DOUBLE PRECISION,
    dosage_unit         VARCHAR(255),
    end_date            DATE NOT NULL,
    start_date_time     TIMESTAMP NOT NULL,
    patient_id          BIGINT REFERENCES patient (id),
    prescription_status VARCHAR(255) NOT NULL,
    time_pattern_id     BIGINT REFERENCES time_pattern (id),
    treatment_id        BIGINT REFERENCES treatment (id)
);

CREATE TABLE event
(
    id              BIGSERIAL PRIMARY KEY,
    version         BIGINT NOT NULL,
    uuid            UUID NOT NULL,
    cancel_reason   VARCHAR(255),
    date_time       TIMESTAMP NOT NULL,
    event_status    VARCHAR(255) NOT NULL,
    patient_id      BIGINT REFERENCES patient (id),
    prescription_id BIGINT REFERENCES prescription (id),
    treatment_id    BIGINT REFERENCES treatment (id)
);
