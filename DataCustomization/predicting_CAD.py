import re
from decimal import Decimal

list_cad_usd = []
list_tsx = []
list_gold = []
list_tsx_modified = []
list_normalized = []

file = open("draft.txt", "r+")
line = file.readline()
while line:
    if re.search(r"\ADec|\ANov",line):
        list = line.split("\t")
        list_cad_usd.append(list[1])

    if re.search(r"\A2019", line):
        list = line.split("\t")
        list_tsx.append([list[2], list[3], list[4]])

    if re.search(r"\A19\w", line):
        list_gold.append(line.replace("\n", ''))
    line = file.readline()

size = len(list_cad_usd)

for smallList in list_tsx:
    high = float(smallList[0].replace(",", ''))
    low = float(smallList[1].replace(",", ''))
    final = float(smallList[2].replace(",", ''))
    converted = (final - low) / (high - low)
    list_tsx_modified.append(converted)

for i in range(0, size):
    list_cad_usd[i] = float(list_cad_usd[i])
    list_gold[i] = float(list_gold[i])

for i in range(0, size - 8):
    if i >= 2:
        expectedOutput = round(list_cad_usd[i] - list_cad_usd[i - 2], 4)
    average_dollar_two_days = (list_cad_usd[i + 1] + list_cad_usd[i + 2]) / 2
    average_dollar_seven_days = 0;
    for j in range (i + 1, i + 8):
        average_dollar_seven_days = average_dollar_seven_days + list_cad_usd[j] / 7
    average_tsx_two_days = (list_tsx_modified[i + 1] + list_tsx_modified[i + 2]) / 2
    average_tsx_seven_days = 0;
    for k in range (i + 1, i + 8):
        average_tsx_seven_days = average_tsx_seven_days + list_tsx_modified[k] / 7
    change_in_gold_two_days = (list_gold[i + 1] - list_gold[i + 2]) / (max(list_gold) - min(list_gold))
    change_in_gold_seven_days = (list_gold[i + 1] - list_gold[i + 8]) / (max(list_gold) - min(list_gold))

    if i >= 2:
        dataset = [average_dollar_two_days, average_dollar_seven_days, average_tsx_two_days, average_tsx_seven_days, change_in_gold_two_days, change_in_gold_seven_days, expectedOutput]
    else:
        dataset = [average_dollar_two_days, average_dollar_seven_days, average_tsx_two_days, average_tsx_seven_days, change_in_gold_two_days, change_in_gold_seven_days]
    list_normalized.append(dataset)


print(list_normalized)
file.close()

dataFile = open("dataset.txt", "a+")
for dataset in list_normalized:
    dataFile.write("%s\n" % str(dataset))

dataFile.close()
