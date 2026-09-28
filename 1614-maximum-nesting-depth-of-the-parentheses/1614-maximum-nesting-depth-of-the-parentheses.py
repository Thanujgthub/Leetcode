class Solution(object):
    def maxDepth(self, s):
        stack = []
        depth = 0
        maxdep = 0
        for i in s:
            if i == ")":
                stack.pop()
                depth -= 1 
            elif i == "(":
                stack.append(i)
                depth += 1 
                maxdep = max(maxdep, depth)
            else:
                continue
        return maxdep
            
        