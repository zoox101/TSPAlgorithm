data = open('data.txt').read()
split = str.split(data, ('\n'))
heuristic = []
for line in split:
    heuristic += str.split(line, ' ')

data = open('max_snake.txt').read()
split = str.split(data, ('\n'))
snake = []
for line in split:
    snake += str.split(line, ' ')

print snake
print heuristic

tuples = []
for i in snake:
    node = int(i)
    hst =  int(heuristic[int(i)-1])
    tuples.append((node, hst))

def count_ops(tuples, value):
    counter = 0
    for tuple in tuples:
        if tuple[1] == value:
            counter += 1
    return counter

for i in xrange(200):
    if count_ops(tuples, i) != 0:
        print str(i) + ': ' + str(count_ops(tuples, i))

string = ''
for tuple in tuples:
    string += str(tuple[1]) + ' '
print string
