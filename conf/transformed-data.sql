INSERT INTO person (person_id, name, surname, username, password, role) VALUES (1, 'Greta', 'Borg', 'borg', 'wl9nk23a', 1);
INSERT INTO person (person_id, name, surname, ssn, email, role) VALUES (2, 'Per', 'Strand', '19671212-1211', 'per@strand.kth.se', 2);

INSERT INTO availability (availability_id, person_id, from_date, to_date) VALUES (1, 2, '2003-02-23', '2003-05-25');
INSERT INTO availability (availability_id, person_id, from_date, to_date) VALUES (2, 2, '2004-02-23', '2004-05-25');

INSERT INTO competence (competence_id, name) VALUES (1, 'Projektledning');
INSERT INTO competence (competence_id, name) VALUES (2, 'Multimediedesign');

INSERT INTO competence_profile (competence_profile_id, person, competence_competence_id, years_of_experience) VALUES (1, 2, 1, 3.5);
INSERT INTO competence_profile (competence_profile_id, person, competence_competence_id, years_of_experience) VALUES (2, 2, 2, 2.0);
