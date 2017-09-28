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
    tuples.append((int(i), int(heuristic[int(i)-1])))

def count_ops(tuples, value):
    counter = 0
    for tuple in tuples:
        if tuple[1] == value:
            counter += 1
    return counter

for i in xrange(200):
    if count_ops(tuples, i) != 0:
        print str(i) + ': ' + str(count_ops(tuples, i))
