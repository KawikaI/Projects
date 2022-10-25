passengers['Age'].fillno(inplace=True,
value=round(passengers['Age'].mean()))
print(passengers)

passengers['FirstClass'] =
passengers['Pclass'].apply(lambda p: 1 if p ==
1 else 0)

passengers['SecondClass'] =
passengers['Pclass'].apply(lambda p: 1 if p ==
2 else 0)

features = passengers[['Sex', 'Age',
'FirstClass', 'SecondClass']]
survival = passengers['Survived']


train_features, test_features, train_labels, test_labels = train_test_split(features, survival)

scaler = StandardScaler()
train_features = scaler.fit_transform(test_features)

model = LogisticRegression()
model.fit(train_features, train_labels)


print(model.score(training_features, train_labels))
