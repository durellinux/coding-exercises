package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

public class Day22SandSlabs {
    private record Point3D(int x, int y, int z) {
        public static Point3D fromInput(String input) {
            String[] data = input.split(",");
            return new Point3D(
                    Integer.valueOf(data[0], 10),
                    Integer.valueOf(data[1], 10),
                    Integer.valueOf(data[2], 10)
            );
        }

        public Point3D displaceZ(int dz) {
            return new Point3D(x, y, z - dz);
        }
    }

    private record Slab(Point3D p1, Point3D p2) {
        public int maxX() {
            return Math.max(p1.x, p2.x);
        }

        public int minX() {
            return Math.min(p1.x, p2.x);
        }

        public int maxY() {
            return Math.max(p1.y, p2.y);
        }

        public int minY() {
            return Math.min(p1.y, p2.y);
        }

        public int maxZ() {
            return Math.max(p1.z, p2.z);
        }

        public int minZ() {
            return Math.min(p1.z, p2.z);
        }

        public static Slab fromInput(String input) {
            String[] pointsData = input.split("~");
            return new Slab(
                    Point3D.fromInput(pointsData[0]),
                    Point3D.fromInput(pointsData[1])
            );
        }

        public boolean isAboveOnXY(Slab other) {
            if (maxX() < other.minX() || other.maxX() < minX() ||
                maxY() < other.minY() || other.maxY() < minY()) {
                return false;
            }

            return true;
        }

        public Slab placeAtZ(int dz) {
            return new Slab(p1.displaceZ(minZ() - dz), p2.displaceZ(minZ() - dz));
        }
    }

    private record SlabsPile(List<Slab> slabs, Map<Slab, List<Slab>> supports, Map<Slab, List<Slab>> supportedBy) {
        public void simulateFall(Slab fallingSlab) {
            List<Slab> supportingSlabs = new ArrayList<>();
            int z = 0;
            for (Slab restingSlab: slabs) {
                if (z == 0) {
                    if (fallingSlab.isAboveOnXY(restingSlab)) {
                        z = restingSlab.maxZ();
                        supportingSlabs.add(restingSlab);
                    }
                } else if(fallingSlab.isAboveOnXY(restingSlab) && restingSlab.maxZ() == z) {
                    supportingSlabs.add(restingSlab);
                } else if (restingSlab.maxZ() != z) {
                    break;
                }
            }

            Slab placedSlab = fallingSlab.placeAtZ(z + 1);
            slabs.add(placedSlab);
            slabs.sort((a, b) -> b.maxZ() - a.maxZ());

            supportedBy.put(placedSlab, supportingSlabs);
            for (Slab supporter: supportingSlabs) {
                List<Slab> supportList = supports.getOrDefault(supporter, new ArrayList<>());
                supportList.add(placedSlab);
                supports.put(supporter, supportList);
            }
        }

    }

    public long solve(List<String> input) {
        List<Slab> slabs = input.stream().map(Slab::fromInput).toList();
        List<Slab> sortedSlabs = slabs.stream().sorted(Comparator.comparingInt(a -> Math.min(a.p1.z, a.p2.z))).toList();
        SlabsPile pile = new SlabsPile(new ArrayList<>(), new HashMap<>(), new HashMap<>());

        for (Slab slab : sortedSlabs) {
            pile.simulateFall(slab);
        }

        List<Slab> removableSlabs = computeRemovable(pile);

        return removableSlabs.size();
    }

    public long solve2(List<String> input) {
        List<Slab> slabs = input.stream().map(Slab::fromInput).toList();
        List<Slab> sortedSlabs = slabs.stream().sorted(Comparator.comparingInt(a -> Math.min(a.p1.z, a.p2.z))).toList();
        SlabsPile pile = new SlabsPile(new ArrayList<>(), new HashMap<>(), new HashMap<>());

        for (Slab slab : sortedSlabs) {
            pile.simulateFall(slab);
        }

        return chainReaction(pile);
    }

    public List<Slab> computeRemovable(SlabsPile slabsPile) {
        return slabsPile.slabs.stream().filter(s -> this.isRemovable(s, slabsPile)).toList();
    }

    private boolean isRemovable(Slab slab, SlabsPile slabsPile) {
        for (Slab supportedSlab: slabsPile.supports.getOrDefault(slab, Collections.emptyList())) {
            if (slabsPile.supportedBy.getOrDefault(supportedSlab, Collections.emptyList()).size() == 1) {
                return false;
            }
        }

        return true;
    }

    public long chainReaction(SlabsPile slabsPile) {
        Set<Slab> safelyRemovable = new HashSet<>(computeRemovable(slabsPile));
        long slabsFalling = 0;
        for (Slab slab: slabsPile.slabs) {
            if (!safelyRemovable.contains(slab)) {
                slabsFalling += computeFallingSlabs(slab, slabsPile);
            }
        }

        return slabsFalling;
    }

    private long computeFallingSlabs(Slab initialSlab, SlabsPile slabsPile) {
        Set<Slab> willFall = new HashSet<>();
        Map<Slab, List<Slab>> supports = slabsPile.supports;
        Map<Slab, List<Slab>> supportedBy = slabsPile.supportedBy;

        willFall.add(initialSlab);
        Queue<Slab> toVisit = new LinkedList<>(supports.get(initialSlab));

        while(!toVisit.isEmpty()) {
            Slab slab = toVisit.poll();
            long validSupportersCount = supportedBy.get(slab).stream().filter(s -> !willFall.contains(s)).count();

            if (validSupportersCount == 0) {
                willFall.add(slab);
            }

            if (supports.containsKey(slab)) {
                toVisit.addAll(supports.get(slab));
            }
        }

        return willFall.size() - 1;
    }


}
