class Solution(object):
    def smallestIndex(self, nums):
        ml = float('inf')
        index = 0
        for i in range(len(nums)):
            sums = 0
            num = str(nums[i])
            for n in num:
                sums += int(n)
            if sums == i:
                return i 
        return -1
        