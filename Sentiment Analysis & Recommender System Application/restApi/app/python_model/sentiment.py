from textblob import TextBlob
import time, sys

def sentiment(lines):
	text = TextBlob(str(lines))
	sentiment_val = text.sentiment.polarity
	if sentiment_val == 0:
		return "Neutral"
	elif sentiment_val > 0:
		return "Positive"
	elif sentiment_val < 0:
		return "Negative"

for line in sys.stdin:
	print(sentiment(line))