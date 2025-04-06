from configparser import ConfigParser
import psycopg2
from os import path

class table_retriever:
    def retrieve(path_2_config, table_name):
        pd = table_retriever.get_parameters(path_2_config)
        return table_retriever.get_result_set(pd, table_name)
        
    def get_parameters(path_2_config):
        result = {}
        parser = ConfigParser()
        parser.read(path_2_config)
        if parser.has_section('postgresql'):
            params = parser.items('postgresql')
            for row in params:
                result[row[0]] = row[1]
        else:
            raise Exception("Section 'postgres' not found in '{1}' file".format(path_2_config))
        # print(result)
        return result

    def get_result_set(connection_parameters, table_name):
        result = None
        try:
            conn = psycopg2.connect(**connection_parameters)
            cur = conn.cursor()
            cur.execute('SELECT * from {}'.format(table_name))
            result = cur.fetchall()
            # print(result)
            cur.close
        except (Exception, psycopg2.DatabaseError) as error:
                print(error)
        finally:
            if conn is not None:
                conn.close()
        return result

# table1 = table_retriever.retrieve(path.join(path.dirname(__file__), "../statics/conf/postgres.ini"), "table1")

# print(table1)