function [theta, J_history] = gradientDescent(X, y, theta, alpha, num_iters, fileID)
%GRADIENTDESCENTMULTI Performs gradient descent to learn theta
%   theta = GRADIENTDESCENTMULTI(x, y, theta, alpha, num_iters, fileID) updates theta by
%   taking num_iters gradient steps with learning rate alpha

% Initialize some useful values
m = length(y); % number of training examples
J_history = zeros(num_iters, 1);
fprintf('Theta intermediate values: \n');
for iter = 1:num_iters
    theta=theta-alpha/m*X'*(X*theta-y);
    
    % Save the cost J in every iteration    
    J_history(iter) = computeCostMulti(X, y, theta);
    
    if iter == 5
        fprintf(fileID,'Theta values after 5th iterations\n');
        fprintf('Theta values after 5th iterations\n');
        fprintf(fileID,' %f \n', theta);
        fprintf(' %f \n', theta);
    elseif iter == 10
        fprintf(fileID,'Theta values after 10th iterations\n');
        fprintf('Theta values after 10th iterations\n');
        fprintf(fileID,' %f \n', theta);
        fprintf(' %f \n', theta); 
    end
end

end
