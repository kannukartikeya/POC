import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
import os


filename = os.path.join("C:/MachineLearning/beer_Recommender/beer_reviews_1.csv")
df = pd.read_csv(filename)
# let's limit things to the top 250
n = 250
top_n = df.beer_name.value_counts().index[:n]
df = df[df.beer_name.isin(top_n)]

print (df.head())
print ("melting...")
df_wide = pd.pivot_table(df, values=["review_overall"],
                         index=["beer_name", "review_profilename"],
                         aggfunc=np.mean).unstack()

# any cells that are missing data (i.e. a user didn't buy a particular product)
# we're going to set to 0
df_wide = df_wide.fillna(0)

# this is the key. we're going to use cosine_similarity from scikit-learn
# to compute the distance between all beers
print ("calculating similarity")
dists = cosine_similarity(df_wide)

# stuff the distance matrix into a dataframe so it's easier to operate on
dists = pd.DataFrame(dists, columns=df_wide.index)

# give the indicies (equivalent to rownames in R) the name of the product id
dists.index = dists.columns


def get_sims(products):
    """
    get_top10 takes a distance matrix an a productid (assumed to be integer)
    and will calculate the 10 most similar products to product based on the
    distance matrix
    dists - a distance matrix
    product - a product id (integer)
    """
    p = dists[products].apply(lambda row: np.sum(row), axis=1)
    p = p.sort_values(ascending=False)
    return p.index[p.index.isin(products) == False]


get_sims(["Sierra Nevada Pale Ale", "120 Minute IPA", "Stone Ruination IPA"])