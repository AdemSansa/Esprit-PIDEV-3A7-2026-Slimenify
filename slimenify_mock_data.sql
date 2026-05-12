-- More Comprehensive Mock Data for Slimenify

-- 1. Users (Role: 'PATIENT', 'THERAPIST', 'ADMIN')
INSERT INTO users (id, first_name, last_name, email, password, role, phone, date_naissance, gender, photo_url) VALUES 
(1, 'Alice', 'Smith', 'alice.smith@example.com', 'hashed_pw_1', 'PATIENT', '+33612345678', '1995-04-12', 'Female', 'alice.jpg'),
(2, 'Bob', 'Johnson', 'bob.johnson@example.com', 'hashed_pw_2', 'PATIENT', '+33687654321', '1988-11-23', 'Male', 'bob.jpg'),
(3, 'Dr. Sarah', 'Connor', 'sarah.connor@example.com', 'hashed_pw_3', 'THERAPIST', '+33611223344', '1980-08-15', 'Female', 'sarah.jpg'),
(4, 'Dr. John', 'Doe', 'john.doe@example.com', 'hashed_pw_4', 'THERAPIST', '+33655667788', '1975-02-05', 'Male', 'john.jpg'),
(5, 'Dr. Emily', 'White', 'emily.white@example.com', 'hashed_pw_5', 'THERAPIST', '+33699887766', '1985-09-30', 'Female', 'emily.jpg'),
(6, 'Charlie', 'Brown', 'charlie.b@example.com', 'hashed_pw_6', 'PATIENT', '+33644332211', '2000-01-10', 'Male', 'charlie.jpg'),
(7, 'Diana', 'Prince', 'diana.p@example.com', 'hashed_pw_7', 'PATIENT', '+33612340000', '1992-06-18', 'Female', 'diana.jpg'),
(8, 'Evan', 'Wright', 'evan.w@example.com', 'hashed_pw_8', 'PATIENT', '+33600112233', '1998-12-05', 'Male', 'evan.jpg'),
(9, 'Admin', 'Super', 'admin@slimenify.com', 'admin_pw', 'ADMIN', '+33600000000', '1990-01-01', 'Other', 'admin.jpg'),
(10, 'Dr. Alan', 'Turing', 'alan.turing@example.com', 'hashed_pw_10', 'THERAPIST', '+33611112222', '1970-07-23', 'Male', 'alan.jpg');

-- 2. Therapists (Using IDs 3, 4, 5, 10 from Users table)
INSERT INTO therapists (id, first_name, last_name, email, password, phone_number, specialization, description, consultation_type, status, photo_url, diploma_path, latitude, longitude, created_at, updated_at) VALUES 
(3, 'Dr. Sarah', 'Connor', 'sarah.connor@example.com', 'hashed_pw_3', '+33611223344', 'Neuropsychologie', 'Experienced in cognitive behavioral therapy with a focus on trauma recovery.', 'Online', 'Active', 'sarah.jpg', '/docs/sarah_diploma.pdf', 48.8566, 2.3522, NOW(), NOW()),
(4, 'Dr. John', 'Doe', 'john.doe@example.com', 'hashed_pw_4', '+33655667788', 'Thérapie de Couple', 'Helping couples find harmony and rebuild communication.', 'In-Person', 'Active', 'john.jpg', '/docs/john_diploma.pdf', 45.7640, 4.8357, NOW(), NOW()),
(5, 'Dr. Emily', 'White', 'emily.white@example.com', 'hashed_pw_5', '+33699887766', 'Pédopsychologie', 'Specialized in child and adolescent developmental psychology.', 'Hybrid', 'Active', 'emily.jpg', '/docs/emily_diploma.pdf', 43.2965, 5.3698, NOW(), NOW()),
(10, 'Dr. Alan', 'Turing', 'alan.turing@example.com', 'hashed_pw_10', '+33611112222', 'Dépression & Anxiété', 'Expert in managing severe depression and generalized anxiety disorders.', 'Online', 'Active', 'alan.jpg', '/docs/alan_diploma.pdf', 43.7102, 7.2620, NOW(), NOW());

-- 3. Review
INSERT INTO review (id_review, content, id_usr) VALUES 
(1, 'Very helpful sessions! I feel much better and more in control of my anxiety.', 1),
(2, 'Highly recommend Dr. John. Helped my wife and I communicate much better.', 2),
(3, 'The online interface is very smooth. Dr. Sarah is an amazing listener.', 6),
(4, 'My son has shown incredible progress with Dr. Emily. Thank you.', 7),
(5, 'Life-changing therapy sessions. Highly professional and empathetic.', 8);

-- 4. Review Reply
INSERT INTO review_reply (id_reply, content, id_review, id_therapist) VALUES 
(1, 'Thank you Alice! I’m so happy to see your progress.', 1, 3),
(2, 'It was a pleasure working with both of you. Keep up the good work!', 2, 4),
(3, 'Thank you Diana, his resilience is truly inspiring.', 4, 5);

-- 5. Event
INSERT INTO event (id, title, description, type, date_start, date_end, location, max_participants, status, image_url, organizer_id) VALUES 
(1, 'Mental Health Workshop', 'Understanding anxiety and depression in modern times.', 'Workshop', '2026-04-10 14:00:00', '2026-04-10 16:00:00', 'Community Center', 50, 'Upcoming', 'workshop.png', 3),
(2, 'Mindfulness Retreat', 'A weekend of guided meditation and reconnecting with oneself.', 'Retreat', '2026-05-15 09:00:00', '2026-05-17 18:00:00', 'Mountain Lodge', 20, 'Upcoming', 'retreat.png', 4),
(3, 'Parenting seminar', 'Navigating teenage years with empathy and boundaries.', 'Seminar', '2026-06-05 10:00:00', '2026-06-05 13:00:00', 'Online Zoom', 100, 'Upcoming', 'parenting.png', 5),
(4, 'Stress Management 101', 'Techniques for managing workplace stress.', 'Webinar', '2026-03-20 18:00:00', '2026-03-20 19:30:00', 'Online', 200, 'Completed', 'stress.png', 10);

-- 6. Registrations
INSERT INTO registrations (id_registration, event_id, participant_name, status, qr_code) VALUES 
(1, 1, 'Alice Smith', 'Confirmed', 'qr_alice_123.png'),
(2, 1, 'Bob Johnson', 'Confirmed', 'qr_bob_456.png'),
(3, 1, 'Charlie Brown', 'Pending', 'qr_charlie_789.png'),
(4, 2, 'Evan Wright', 'Confirmed', 'qr_evan_012.png'),
(5, 3, 'Diana Prince', 'Confirmed', 'qr_diana_345.png'),
(6, 4, 'Alice Smith', 'Attended', 'qr_alice_old.png'),
(7, 4, 'Evan Wright', 'Attended', 'qr_evan_old.png');

-- 7. Quiz
INSERT INTO quiz (id, title, description, category, total_questions, active, min_score, max_score, created_at, updated_at) VALUES 
(1, 'Anxiety Assessment', 'A quiz to evaluate generalized anxiety levels.', 'Anxiety', 3, true, 0, 15, NOW(), NOW()),
(2, 'Depression Screening', 'Understand your mood and energy levels over the past 2 weeks.', 'Depression', 4, true, 0, 20, NOW(), NOW()),
(3, 'Relationship Health Scale', 'Evaluate communication and satisfaction in your relationship.', 'Couples', 3, true, 0, 15, NOW(), NOW());

-- 8. Question
INSERT INTO question (id, question_text, required, image_path) VALUES 
(1, 'How often do you feel nervous or on edge?', true, null),
(2, 'Do you have trouble sleeping?', true, null),
(3, 'Do you feel excessively worried about different things?', true, null),
(4, 'How often do you feel little interest or pleasure in doing things?', true, null),
(5, 'Do you feel down, depressed, or hopeless?', true, null),
(6, 'Do you feel tired or have little energy?', true, null),
(7, 'Do you have poor appetite or tend to overeat?', true, null),
(8, 'How often do you and your partner resolve arguments peacefully?', true, null),
(9, 'Do you feel emotionally supported by your partner?', true, null),
(10, 'How often do you spend quality time together?', true, null);

-- 9. Quiz Question Mapping
INSERT INTO quiz_question (quiz_id, question_id) VALUES 
(1, 1), (1, 2), (1, 3), 
(2, 4), (2, 5), (2, 6), (2, 7), 
(3, 8), (3, 9), (3, 10);

-- 10. Quiz Results
INSERT INTO quiz_results (id, user_id, quiz_id, score, result, mood, taken_at) VALUES 
(1, 1, 1, 8, 'Moderate Anxiety', 'Anxious', '2026-03-01 10:00:00'),
(2, 2, 1, 3, 'Low Anxiety', 'Calm', '2026-03-05 14:30:00'),
(3, 6, 2, 12, 'Moderate Depression', 'Fatigued', '2026-03-10 09:15:00'),
(4, 7, 2, 4, 'Minimal Depression', 'Neutral', '2026-03-15 16:45:00'),
(5, 8, 3, 14, 'Healthy Relationship', 'Happy', '2026-03-20 20:00:00');

-- 11. Appointment
INSERT INTO appointment (id, appointment_date, start_time, end_time, status, type, therapist_id, patient_id) VALUES 
(1, '2026-04-05', '10:00:00', '11:30:00', 'Confirmed', 'Online', 3, 1),
(2, '2026-04-06', '14:00:00', '15:30:00', 'Pending', 'In-Person', 4, 8),
(3, '2026-04-07', '09:00:00', '10:30:00', 'Confirmed', 'Hybrid', 5, 7),
(4, '2026-04-08', '16:00:00', '17:30:00', 'Confirmed', 'Online', 10, 6),
(5, '2026-03-10', '11:00:00', '12:30:00', 'Completed', 'Online', 3, 1),
(6, '2026-03-12', '15:00:00', '16:30:00', 'Completed', 'In-Person', 4, 2),
(7, '2026-03-15', '10:00:00', '11:30:00', 'Cancelled', 'Online', 10, 8);

-- 12. Note
INSERT INTO note (id, content, mood, created_at, appointment_id, therapist_id) VALUES 
(1, 'Patient expressed mild stress about work. Discussed cognitive reframing mechanisms.', 'Stressed', '2026-03-10 13:00:00', 5, 3),
(2, 'Couple explored communication barriers. Assigned listening exercises for the week.', 'Hopeful', '2026-03-12 17:00:00', 6, 4);

-- 13. Availabilities
INSERT INTO availabilities (id, day, start_time, end_time, is_available, therapist_id, specific_date) VALUES 
(1, 'MONDAY', '09:00:00', '17:00:00', true, 3, null),
(2, 'TUESDAY', '14:00:00', '18:00:00', true, 4, null),
(3, 'WEDNESDAY', '10:00:00', '12:00:00', false, 3, '2026-04-08'),
(4, 'THURSDAY', '08:00:00', '12:00:00', true, 5, null),
(5, 'FRIDAY', '13:00:00', '19:00:00', true, 10, null),
(6, 'MONDAY', '10:00:00', '15:00:00', true, 4, null),
(7, 'WEDNESDAY', '09:00:00', '16:00:00', true, 10, null),
(8, 'FRIDAY', '15:00:00', '18:00:00', false, 5, '2026-04-10');
