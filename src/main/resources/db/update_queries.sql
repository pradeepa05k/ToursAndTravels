SELECT 
   table_name, 
   column_name, 
   data_type 
FROM 
   information_schema.columns
WHERE 
   table_name = 'bus';
   
ALTER TABLE bus
ALTER COLUMN boarding_point TYPE text[][];

ALTER TABLE bus
ALTER COLUMN dropping_point TYPE text[][];