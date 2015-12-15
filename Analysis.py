__author__ = 'nav'

import csv
import numpy as np
import pandas as pd
import scipy
from sklearn import decomposition
from sklearn import linear_model
from sklearn import cross_validation
from sklearn.metrics import mean_squared_error
from sklearn.lda import LDA
from sklearn.neighbors import KNeighborsRegressor as KNRegressor

with open('ReWorkingDataset1.csv', 'rt', encoding='utf-8') as csvfile:
    reader = csv.DictReader(csvfile)
    reader2 = pd.read_csv('ReWorkingDataset1.csv',encoding='utf-8')

    columnsOfInterest = ['ACCELEROMETER_X','ACCELEROMETER_Y','ACCELEROMETER_Z',
                         'GRAVITY_X','GRAVITY_Y','GRAVITY_Z','LINEAR ACCELERATION_X',
                         'LINEAR ACCELERATION_Y','LINEAR ACCELERATION_Z',
                         'GYROSCOPE_X','GYROSCOPE_Y','GYROSCOPE_Z','LIGHT',
                         'MAGNETIC FIELD_X','MAGNETIC FIELD_Y','MAGNETIC FIELD_Z',
                         'ORIENTATION_Z','ORIENTATION_X','ORIENTATION_Y',
                         'PROXIMITY','ATMOSPHERIC PRESSURE','SOUND LEVEL',
                         'Latitude','Longitude','Altitude','Altitude-google']
    regularData = np.array(columnsOfInterest)

    normalizedData = np.empty(shape=(np.size(reader2['ACCELEROMETER_X']),1))
    for col in columnsOfInterest:
        dataColumn = reader2[col]
        colMin = np.amin(dataColumn)
        colMax = np.amax(dataColumn)
        writeVector = np.empty(shape=(np.size(dataColumn),1))
        for i in range(0, np.size(dataColumn)):
            normalizedVal = (dataColumn[i] - colMin)/(colMax - colMin)
            writeVector[i] = normalizedVal
        normalizedData = np.hstack([normalizedData,writeVector])

    normalizedData = np.delete(normalizedData, 0, 1)

    pd.DataFrame(normalizedData).to_csv('NormalizedData.csv',encoding='utf-8', index=False,
                                        header=columnsOfInterest)

    # Get non-normalized data
    for row in reader:
        newrow = list(row[col] for col in columnsOfInterest)
        regularData = np.vstack([regularData, newrow])

    regularData = np.delete(regularData, (0), axis = 0)
    regularData = regularData.astype(float)
    print(regularData.shape[1])
    #print(data[:,29])

    # Get response variable
    yColumn = reader2['Radians'].astype(float)

    # Cross-Validation
    X_train, X_test, y_train, y_test = cross_validation.train_test_split(normalizedData,
                                                                         yColumn,
                                                                         test_size=0.4,
                                                                         random_state = 0)
    # Multivariate Linear Regression
    # clf = linear_model.LinearRegression()
    # clf.fit(X_train, y_train)
    # print(clf.coef_)
    # print(clf.score(X_test,y_test))
    #
    # predictColumn = clf.predict(X_test)
    # print(mean_squared_error(y_test, predictColumn))

    # PCA
    pca = decomposition.PCA()
    pca.fit(X_train)
    print(pca.explained_variance_)

    pca.n_components = 5
    reducedTrainSet = pca.fit_transform(X_train)

    pcaClf = linear_model.LinearRegression()
    pcaClf.fit(reducedTrainSet,y_train)

    reducedTestSet = pca.transform(X_test)
    print(pcaClf.score(reducedTestSet,y_test))

    predictColumn = pcaClf.predict(reducedTestSet)
    print(mean_squared_error(y_test, predictColumn))

    print('KNN')
    neighReg = KNRegressor(n_neighbors = 10)
    neighReg.fit(X_train,y_train)
    print(neighReg.score(X_test,y_test))
    predictColumn = neighReg.predict(X_test)
    print(mean_squared_error(y_test, predictColumn))

    #
    # Latent Discriminant Analysis
    # linDA = LDA()
    # linDA.fit(X_train,y_train)
    