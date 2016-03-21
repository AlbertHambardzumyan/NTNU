%% Machine Learning 
%  Exercise 1: Linear regression model with multiple variables

%% ================ Part 1: ================
%% Clearing and Closing Figures
clear ; close all; clc
fileID = fopen('log.txt','w');

%% Loading Data
fprintf(fileID,'Loading data ...\n');
fprintf('Loading data ...\n');
data = load('data-train.csv');
X = data(:, 1:2);
y = data(:, 3);
m = length(y);

%Adding intercept term to X (Add a column of ones to x)
X = [ones(m, 1) X];

fprintf(fileID,'End of Initialization ... Press enter to continue.\n');
fprintf('End of Initialization ... Press enter to continue.\n');
pause;

%% ================ Part 2: Gradient Descent ================
% Choose some alpha value
alpha = 0.01;
num_iters = 400;

% Initing Theta and Running Gradient Descent 
theta = zeros(3, 1);

fprintf(fileID,'\nInitail values \n');
fprintf('\nInitail values \n');
fprintf(fileID,'Alpha: %f   Iterations: %f\n', alpha, num_iters);
fprintf('Alpha: %f   Iterations: %f\n', alpha, num_iters);
fprintf(fileID,'\nTheta values \n');
fprintf('\nTheta values \n');
fprintf(fileID,' %f \n', theta);
fprintf(' %f \n', theta);
fprintf(fileID,'\nRunning gradient descent ...\n');
fprintf('\nRunning gradient descent ...\n');
[theta, J_history] = gradientDescent(X, y, theta, alpha, num_iters, fileID);
fprintf(fileID,'End of gradient descent ... Press enter to continue\n');
fprintf('End of gradient descent ... Press enter to continue\n');
pause;

% Displaying gradient descent's result
fprintf(fileID,'\nTheta computed from gradient descent: \n');
fprintf('\nTheta computed from gradient descent: \n');
fprintf(fileID,' %f \n', theta);
fprintf(' %f \n', theta);

% Ploting the convergence graph
fprintf(fileID,'\nPlot the convergence graph...\n');
fprintf('\nPlot the convergence graph...\n');
figure;
plot(1:numel(J_history), J_history, '-b', 'LineWidth', 2);
xlabel('Number of iterations');
ylabel('Cost J');
fprintf(fileID,'End of Plot the convergence graph ... Press enter to continue\n');
fprintf('End of Plot the convergence graph ... Press enter to continue\n');
pause;

%% ================ Part 3: Test ================
fprintf(fileID,'\nTraining is Done. Test can be started ... Press enter to continue\n');
fprintf('\nTraining is Done. Test can be started ... Press enter to continue\n');
pause;
fprintf(fileID,'Loading data ...\n');
fprintf('Loading data ...\n');
%% Loading Data
data = load('data-test.csv');
X = data(:, 1:2);
y = data(:, 3);
m = length(y);

%Adding intercept term to X (Add a column of ones to x)
X = [ones(m, 1) X];

fprintf(fileID,'Printing The Result...\n');
fprintf('Printing The Result...\n');
for iter = 1:m
    predicted_y = X(iter, 1)*theta(1) + X(iter, 2)*theta(2) + X(iter, 3)*theta(3);
    fprintf(fileID,' Actual value = %f        Predicted value = %f \n', y(iter), predicted_y);
    fprintf(' Actual value = %f        Predicted value = %f \n', y(iter), predicted_y);
end
fclose(fileID);