CREATE TABLE doctor
(
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    name VARCHAR(255)
);

CREATE TABLE patient
(
    id               BIGSERIAL PRIMARY KEY,
    version          BIGINT NOT NULL,
    diagnosis        VARCHAR(255),
    insurance_number VARCHAR(255) UNIQUE NOT NULL,
    name             VARCHAR(255),
    patient_status   INTEGER,
    doctor_id        BIGINT REFERENCES doctor
);

CREATE TABLE treatment
(
    id      BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL,
    name    VARCHAR(255),
    type    INTEGER
);

CREATE TABLE event
(
    id            BIGSERIAL PRIMARY KEY,
    version       BIGINT NOT NULL,
    cancel_reason VARCHAR(255),
    date_time     TIMESTAMP,
    event_status  INTEGER,
    patient_id    BIGINT REFERENCES patient,
    treatment_id  BIGINT REFERENCES treatment
);

CREATE TABLE time_pattern
(
    id              BIGSERIAL PRIMARY KEY,
    version         BIGINT NOT NULL,
    daily_frequency INTEGER NOT NULL,
    time_basis      INTEGER
);

CREATE TABLE time_pattern_element
(
    id              BIGSERIAL PRIMARY KEY,
    version         BIGINT NOT NULL,
    day_of_week     INTEGER,
    time_pattern_id BIGINT REFERENCES time_pattern
);

CREATE TABLE prescription
(
    id              BIGSERIAL PRIMARY KEY,
    version         BIGINT NOT NULL,
    dosage          DOUBLE PRECISION NOT NULL,
    dosage_unit     VARCHAR(255),
    end_date_time   TIMESTAMP,
    start_date_time TIMESTAMP,
    patient_id      BIGINT REFERENCES patient,
    time_pattern_id BIGINT REFERENCES time_pattern,
    treatment_id    BIGINT REFERENCES treatment
);