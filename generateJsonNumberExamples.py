#!/usr/bin/env python

import random

def randomDigit():
    return random.randint(1, 10)

def buildJson():
    for minus in ('-' , ''):
        print buildDigits(minus)

def buildDigits(front):
    for digit in ('', 0, 1, 15820):
        if digit != '' and digit %2 == 0:
            buildDecimal(front + str(digit) + '.')
            continue
        buildExponent(front + str(digit))

def buildDecimal(front):
    for decimal in (0, 1, 15829):
        buildExponent(front + str(decimal))

def buildExponent(front):
    for exponent in ('', 'e', 'E'):
        if exponent == '':
            print front
            continue
        buildExponand(front + exponent)

def buildExponand(front):
    for power in ('', '+', '-'):
        for exponand in (0,1, 15820):
            print front + power + str(exponand)

buildJson()

