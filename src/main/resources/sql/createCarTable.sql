USE testGM;

DROP TABLE IF EXISTS makes;
DROP TABLE IF EXISTS models;
DROP TABLE IF EXISTS model_years;

CREATE TABLE makes(
	make_id INT NOT NULL AUTO_INCREMENT,
	make_name varchar(128),
	PRIMARY KEY(make_id)
);

CREATE TABLE models(
	model_id INT NOT NULL AUTO_INCREMENT,
    make_id INT NOT NULL,
	model_name varchar(128),
	PRIMARY KEY(model_id)
);

CREATE TABLE model_years(
	model_id INT NOT NULL,
	year_name varchar(128),
	PRIMARY KEY(model_id, year_name)
);
    

insert into makes (make_name) values ('Chevrolet'),('Buick'),('Cadillac'),('GMC');

insert into models (make_id, model_name) values (1, 'Cruze'),(1, 'sonic'),(1, 'spark'),(1, 'captiva'),(1, 'ss'),
						                        (1, 'Impala'),(1, 'malibu'),(1, 'camaro'),(1, 'corvette'),(1, 'equinox'),
						                        (1, 'Traverse'),(1, 'tahoe'),(1, 'suburban'),(1, 'express'),(1, 'trax'),
                                                (1, 'Savana'),(1, 'avalanche'),(1, 'silverado'),(1, 'colora1do');
insert into models (make_id, model_name) values (2, 'Verano'),(2, 'lacrosse'),(2, 'regal'),(2, 'encore'),(2, 'enclave');
insert into models (make_id, model_name) values (3, 'CTS'),(3, 'xts'),(3, 'ats'),(3,'cts-v'),(3, 'srx'),(3, 'escalade');
insert into models (make_id, model_name) values (4, 'Terrain'),(4, 'acadia'),(4, 'yukon'),(4, 'sierra');

insert into model_years values (1, '2014'), (1, '2015');
insert into model_years values (2, '2014'), (2, '2015');