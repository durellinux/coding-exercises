package com.gianlucadurelli.coding.leetcode;

// https://leetcode.com/problems/merge-sorted-array/?envType=study-plan-v2&envId=top-interview-150
public class MergedSortedArray {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int im = m - 1;
        int in = n - 1;

        int i = m + n - 1;

        while(i >= 0) {
            if (im < 0) {
                nums1[i] = nums2[in];
                in--;
                i--;
            } else if (in < 0) {
                nums1[i] = nums1[im];
                im--;
                i--;
            } else {
                int vm = nums1[im];
                int vn = nums2[in];

                if (vm >= vn) {
                    nums1[i] = vm;
                    im--;
                } else {
                    nums1[i] = vn;
                    in--;
                }

                i--;
            }
        }
    }


    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int[] sorted = new int[m + n];

        int im = 0;
        int in = 0;

        int i = 0;
        while(im < m || in < n) {
            if (im == m) {
                sorted[i] = nums2[in];
                in++;
                i++;
            } else if (in == n) {
                sorted[i] = nums1[im];
                im++;
                i++;
            } else {
                int vm = nums1[im];
                int vn = nums2[in];

                if (vm <= vn) {
                    sorted[i] = vm;
                    im++;
                } else {
                    sorted[i] = vn;
                    in++;
                }

                i++;
            }
        }

        for (i = 0; i < m + n; i++) {
            nums1[i] = sorted[i];
        }
    }
}
