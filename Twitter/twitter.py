import tweepy
from transformers import pipeline
import sys
sys.path.append('../../../../SECRETS')
import TWITTER_API

client = tweepy.Client(TWITTER_API.BEARER_TOKEN)

user = client.get_user(username = 'elonmusk')
response = client.get_users_tweets(user.data['id'],
                                  max_results=100,
                                  exclude='replies')


tweets = [response.data[8]['text'],
         response.data[9]['text']]
print(tweets)


sentiment_pipeline = pipeline("sentiment-analysis",
        model = 'cardiffnlp/twitter-roberta-base-sentiment')
output = sentiment_pipeline(tweets)



