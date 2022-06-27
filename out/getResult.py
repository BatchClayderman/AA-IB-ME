import os
from sys import exit
from pandas import DataFrame as DF
os.chdir(os.path.abspath(os.path.dirname(__file__)))#解析进入程序所在目录
EXIT_SUCCESS = 0
EXIT_FAILURE = 1
EOF = (-1)
columns = ["Test", "d", "k", "n", "EKGen_Time", "EKGen_Space", "DKGen_Time", "DKGen_Space", "Enc_Time", "Enc_Space", "Dec_Time", "EKeySanity_Time", "DKeySanity_Time"]


def main():
	array_lists = []
	try:
		for key_value in result:
			lists = []
			for key in columns:
				lists.append(key_value[key])
			array_lists.append(lists)
		pf = DF(array_lists, columns = columns)
		pf.to_csv("result.csv", index = False)
		input("{0}\n\n{1}\n".format(result, pf))
		return EXIT_SUCCESS
	except Exception as e:
		input("Error/Exception: {0}".format(e))
		return EXIT_FAILURE



if __name__ == "__main__":
	result = eval(input("Input your result here: \n"))
	exit(main())