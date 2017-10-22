import matplotlib.pyplot as plot

file = open('point_test.csv').read()

matrix = []
lines = str.split(file, '\n')
for line in lines:
    matrix.append([])
    split = str.split(line, ',')
    for num in split:
        matrix[-1].append(num)

for array in matrix:
    plot.plot(array[0], array[2], 'bo')

for array in matrix:
    if array[0] == array[1]:
        plot.plot(array[0], array[2], 'ro')

for array in matrix:
    if array[1] == '4':
        plot.plot(array[0], array[2], 'yo')

plot.show()
