select 'CREATE INDEX idx_'|| table_name || '_' || column_name || ' on vsmtest_2.' || table_name ||  '(' || column_name || ');' from information_schema.columns where table_schema = 'vsmtest_2' and "column_name"  = 'id';



select 'CREATE INDEX idx_'|| table_name || '_' || column_name || ' on vsmtest_2.' || table_name ||  '(' || column_name || ');'
 From information_schema.columns where table_schema = 'vsmtest_2' and "table_name" like 'rel_%'



select distinct temp.sql From (
SELECT
		'idx_' || tc.table_name || '_' || kcu.column_name as idxname,
    'CREATE INDEX idx_'|| tc.table_name || '_' || kcu.column_name || ' on vsmtest_2.' || tc.table_name ||  '(' || kcu.column_name || ');' as sql
FROM 
    information_schema.table_constraints AS tc 
    JOIN information_schema.key_column_usage AS kcu
      ON tc.constraint_name = kcu.constraint_name
      AND tc.table_schema = kcu.table_schema
    JOIN information_schema.constraint_column_usage AS ccu
      ON ccu.constraint_name = tc.constraint_name
      AND ccu.table_schema = tc.table_schema
WHERE tc.constraint_type = 'FOREIGN KEY' AND tc.table_schema = 'vsmtest_2' ) temp
where temp.idxname not in (select indexname From pg_indexes);






CREATE INDEX idx_request_data_status_id on vsmtest_2.request_data(status_id);
 
CREATE INDEX idx_request_data_created_id_status_id on vsmtest_2.request_data(created_id, status_id);
 
CREATE INDEX idx_request_data_created_id on vsmtest_2.request_data(created_id);