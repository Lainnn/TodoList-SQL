SET FOREIGN_KEY_CHECKS=0;  -- temporarily disable foreign key checking
DROP TABLE IF EXISTS Uncompleted;
DROP TABLE IF EXISTS Completed;
-- DROP TABLE lines for all tables in schema
SET FOREIGN_KEY_CHECKS=1; 

CREATE TABLE Uncompleted(
	id INT AUTO_INCREMENT,
	title VARCHAR(60) NOT NULL,
	discription VARCHAR(300),
	dueDate DATE,
    createDate DATETIME,

	PRIMARY KEY (id)
);

CREATE TABLE Completed(
	id INT AUTO_INCREMENT,
    title VARCHAR(60) NOT NULL,
    discription VARCHAR(300),
    dueDate DATE,
    completeDate DATETIME,
    createDate DATETIME,
    
    PRIMARY KEY (id)
);
