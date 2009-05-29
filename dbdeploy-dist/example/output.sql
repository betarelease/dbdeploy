

 ----- START CHANGE SCRIPT #1: 001_create_table.sql ----- 




-- Most databases don't apply DDL statements transactionally.
-- Therefore, to recover from failure more easily, only put a single DDL
-- statement in each change script.

CREATE TABLE Test (id INTEGER);



INSERT INTO changelog (change_number, delta_set, complete_dt, applied_by, description)
 VALUES (1, 'Main', CURRENT_TIMESTAMP, USER(), '001_create_table.sql');

COMMIT;

 ----- END CHANGE SCRIPT #1: 001_create_table.sql ----- 


 ----- START CHANGE SCRIPT #2: 002_insert_data.sql ----- 



-- dbdeploy will wrap the application of each change script
-- in a transaction
--
-- DML statements (INSERT, UPDATE etc) can be applied transactionally,
-- so therefore dbdeploy will ensure that either the whole of this script applies
-- or none of it does.
--
INSERT INTO Test VALUES (6);
INSERT INTO Test VALUES (7);



INSERT INTO changelog (change_number, delta_set, complete_dt, applied_by, description)
 VALUES (2, 'Main', CURRENT_TIMESTAMP, USER(), '002_insert_data.sql');

COMMIT;

 ----- END CHANGE SCRIPT #2: 002_insert_data.sql ----- 

