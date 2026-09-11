class Solution(object):
    def twoSum(self, nums, target):
        seen = {}

        for i,num in enumerate(nums):
            goal = target - num 

            if goal in seen:
                return [seen[goal],i]

            seen[num] = i
        
