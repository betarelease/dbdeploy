

 ----- START CHANGE SCRIPT #2: 002_insert_data.sql ----- 




DELETE FROM Test WHERE id IN (6,7);



DELETE FROM changelog WHERE change_number = 2 AND delta_set = 'Main';

COMMIT;

 ----- END CHANGE SCRIPT #2: 002_insert_data.sql ----- 


 ----- START CHANGE SCRIPT #1: 001_create_table.sql ----- 




DROP TABLE Test;


DELETE FROM changelog WHERE change_number = 1 AND delta_set = 'Main';

COMMIT;

 ----- END CHANGE SCRIPT #1: 001_create_table.sql ----- 

