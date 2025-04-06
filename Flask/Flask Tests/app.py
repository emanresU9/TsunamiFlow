from flask import Flask, render_template, url_for
from statics.table_retriever import table_retriever
from os import path

app = Flask(__name__, template_folder='templates', static_folder='statics')

# List of routes. /, /table_from_array, ...

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/table_from_array')
def table_from_array():
    tableData = [["dog", 2, "George"],["Cat", 1, "Harris"], ["Mouse", 5, "pepper"]]
    return render_template('table_from_array.html', tableData=tableData)


@app.route('/DatabaseConnection')
def databse_connection():
    config_path = path.join(app.static_folder, "conf/postgres.ini")
    set = table_retriever.retrieve(config_path, "table1")
    # print(set)
    return render_template('DatabaseConnection.html', set=set)


if __name__ == '__main__':
    app.run(debug=True)