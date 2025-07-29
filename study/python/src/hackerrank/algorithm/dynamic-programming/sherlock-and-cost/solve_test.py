#!/bin/python3

import unittest

from solve import cost

class TestSherlockAndCost(unittest.TestCase):
    
    def test_small_examples(self):
        """Test with small example arrays"""
        # Example 1: [100, 2, 100, 2, 100]
        # Optimal array would be [100, 1, 100, 1, 100] with cost = 99 + 99 + 99 + 99 = 396
        self.assertEqual(cost([100, 2, 100, 2, 100]), 396)
        
        # Example 2: [1, 2, 3]
        # Optimal array would be [1, 2, 1] with cost = 1 + 1 = 2
        self.assertEqual(cost([1, 2, 3]), 2)
