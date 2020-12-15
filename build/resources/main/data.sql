insert into location(id,city,state,latitude,longitude,weather_id) values(1,'Fremont','CA',44.5,44.5,null);
INSERT INTO weather (id,date_recorded, temperature, location_id) VALUES(1,CURRENT_TIMESTAMP(),45.7,null);
update weather set location_id=1 where id=1;
update location set weather_id=1 where id=1;
