SET search_path TO mrschema;

INSERT INTO "user"(login, password, name, role, version)
VALUES ('ericforeman', '$2a$10$YrRfHkTFB/.hBty5z7idG.ooajgvnzoV2BzXRsnHVX7VU4Q8BckbK',
        'Eric Foreman', 'DOCTOR', 0),
       ('robertchase', '$2a$10$YrRfHkTFB/.hBty5z7idG.ooajgvnzoV2BzXRsnHVX7VU4Q8BckbK',
        'Robert Chase', 'DOCTOR', 0),
       ('admin', '$2a$10$9zfPE5JcD96gUkeNx..qaeJDyj74bwGH/TeGfu6YW8t/Nzj.BvKPO',
        'Admin', 'ADMIN', 0),
       ('wendy', '$2a$10$QS3Zs/XhxbHJfDLknQ0l2uHysTyedxBLU7BPYAZrZ2aLvMPQj5m0q',
        'Wendy Quinn', 'NURSE', 0);

INSERT INTO doctor(user_id, version)
VALUES (1, 0),
       (2, 0);

INSERT INTO treatment(name, type, version, deleted)
VALUES ('A-poxide (Chlordiazepoxide)', 'MEDICINE', 0, false),
       ('Demeclocycline', 'MEDICINE', 0, false),
       ('Cardiovascular strengthening', 'PROCEDURE', 0, false),
       ('Physiotherapy', 'PROCEDURE', 0, false),
       ('Massage', 'PROCEDURE', 0, false),
       ('Papaverine', 'MEDICINE', 0, false);

INSERT INTO patient(diagnosis, insurance_number, name, patient_status, doctor_id, version)
VALUES ('Anxiety/agitation', '214-376-768-76', 'John Kelley', 'BEING_TREATED', 1, 0),
       ('Blood circulation failure', '548-878-952-58', 'Emily Lawson', 'BEING_TREATED', 2, 0),
       ('Respiratory tract infection', '654-213-899-57', 'Henry Arrington', 'BEING_TREATED', 1, 0);