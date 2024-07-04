DROP TABLE IF EXISTS hits;
CREATE TABLE IF NOT EXISTS hits (
id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
app VARCHAR(20) NOT NULL,
uri VARCHAR NOT NULL,
ip VARCHAR(15) NOT NULL,
timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
CONSTRAINT pk_hits PRIMARY KEY (id)
);