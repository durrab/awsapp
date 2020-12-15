
DROP TABLE IF EXISTS Location CASCADE;
DROP TABLE IF EXISTS Weather CASCADE;
  
CREATE TABLE Weather (
  id INT AUTO_INCREMENT PRIMARY KEY,
  date_recorded DATE NOT NULL,
  temperature FLOAT NOT NULL,
  location_id INT
);

CREATE TABLE Location (
id INT AUTO_INCREMENT PRIMARY KEY,
city VARCHAR(250) NOT NULL,
state VARCHAR(250) NOT NULL,
latitude FLOAT NOT NULL,
longitude FLOAT NOT NULL,
weather_id INT);

ALTER TABLE Weather ADD FOREIGN KEY (location_id) REFERENCES Location(id);
ALTER TABLE Location ADD FOREIGN KEY (weather_id) REFERENCES Weather(id);

