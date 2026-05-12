-- Database Structure extracted from Java backend code

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(50),
    phone VARCHAR(20),
    date_naissance DATE,
    gender VARCHAR(20),
    photo_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS therapists (
    id INT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    phone_number VARCHAR(20),
    specialization VARCHAR(255),
    description TEXT,
    consultation_type VARCHAR(50),
    status VARCHAR(50),
    photo_url VARCHAR(255),
    diploma_path VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS review (
    id_review INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT,
    id_usr INT,
    FOREIGN KEY (id_usr) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS review_reply (
    id_reply INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT,
    id_review INT,
    id_therapist INT,
    FOREIGN KEY (id_review) REFERENCES review(id_review) ON DELETE CASCADE,
    FOREIGN KEY (id_therapist) REFERENCES therapists(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS event (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    type VARCHAR(50),
    date_start DATETIME,
    date_end DATETIME,
    location VARCHAR(255),
    max_participants INT,
    status VARCHAR(50),
    image_url VARCHAR(255),
    organizer_id INT,
    FOREIGN KEY (organizer_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS registrations (
    id_registration INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    participant_name VARCHAR(255),
    status VARCHAR(50),
    qr_code VARCHAR(255),
    FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS quiz (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    category VARCHAR(50),
    total_questions INT,
    active BOOLEAN,
    min_score INT,
    max_score INT,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS question (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT,
    required BOOLEAN,
    image_path VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS quiz_question (
    quiz_id INT,
    question_id INT,
    PRIMARY KEY (quiz_id, question_id),
    FOREIGN KEY (quiz_id) REFERENCES quiz(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS quiz_results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    quiz_id INT,
    score INT,
    result VARCHAR(255),
    mood VARCHAR(50),
    taken_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (quiz_id) REFERENCES quiz(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS appointment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_date DATE,
    start_time TIME,
    end_time TIME,
    status VARCHAR(50),
    type VARCHAR(50),
    therapist_id INT,
    patient_id INT,
    FOREIGN KEY (therapist_id) REFERENCES therapists(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS note (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT,
    mood VARCHAR(50),
    created_at DATETIME,
    appointment_id INT,
    therapist_id INT,
    FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE CASCADE,
    FOREIGN KEY (therapist_id) REFERENCES therapists(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS availabilities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    day VARCHAR(20),
    start_time TIME,
    end_time TIME,
    is_available BOOLEAN,
    therapist_id INT,
    specific_date DATE,
    FOREIGN KEY (therapist_id) REFERENCES therapists(id) ON DELETE CASCADE
);
