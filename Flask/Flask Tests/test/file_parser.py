from configparser import ConfigParser
from os import path

parser = ConfigParser()
config_path = path.abspath(path.join(path.dirname(__file__), "../../statics/conf/postgres.ini"))
parser.read(config_path)
print(parser.sections())