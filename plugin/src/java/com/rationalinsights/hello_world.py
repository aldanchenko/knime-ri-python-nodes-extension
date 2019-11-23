import pandas as pd
import pandas

count = options['Count']

# Using input_table by having a option with the column name
# and use Pandas column selection to get values of column
# id_column = options['id_column']
# ids = input_table[id_column]

data = []
for i in range(count):
    row = {}
    row['Column 0'] = 'String_{0}'.format(i)
    row['Column 1'] = 0.5 * i
    row['Column 2'] = i
    data.append(row)

output_table = pd.DataFrame(data)
