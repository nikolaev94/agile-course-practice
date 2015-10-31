package ru.unn.agile.IntersectionOfSegments;

enum TypeOfIntersection {
    NotIntersection, IntersectionInOnePoint, OnePartOfOther, SegmentsHaveCommonPart
}

public class Segment {
    private Point start;
    private Point finish;
    private double lengthSegment;

    Segment(final Point start, final Point finish) {
        this.start = start;
        this.finish = finish;
    }

    public void setStart(final Point start) {
        this.start = start;
    }

    public void setFinish(final Point finish) {
        this.finish = finish;
    }

    public Point getStart() {
        return start;
    }

    public Point getFinish() {
        return finish;
    }

    public double getStartX() {
        return start.getX();
    }

    public double getFinishX() {
        return finish.getX();
    }

    public double getStartY() {
        return start.getY();
    }

    public double getFinishY() {
        return finish.getY();
    }

    public double getLengthSegment() {
        return calculationLengthOfSegment();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(final Object object) {
        Segment segment = (Segment) object;
        return this.getStart() == segment.getStart() && this.getFinish() == segment.getFinish();
    }

    public Intersection isIntersectedWith(final Segment segment) {
        return findIntersection(this, segment);
    }

    private Intersection findIntersection(final Segment segment1, final Segment segment2) {
        TypeOfIntersection typeOfIntersection;
        Point startPointOfIntersection;
        Point finishPointOfIntersection;

        if (segment1.isIntersection(segment2)) {
            Double k1 = calculateSlope(segment1);
            Double k2 = calculateSlope(segment2);

            double b1 = segment1.getStartY() - k1 * segment1.getStartX();
            double b2 = segment2.getStartY() - k2 * segment2.getStartX();

            if (k1.equals(k2)) {
                makeFirstPointWithLessX(segment1);
                makeFirstPointWithLessX(segment2);
                if (segment1.getStartX() < segment2.getStartX()
                        && segment1.getFinishX() > segment2.getFinishX()) {
                    typeOfIntersection = TypeOfIntersection.OnePartOfOther;
                    startPointOfIntersection = segment2.getStart();
                    finishPointOfIntersection = segment2.getFinish();
                } else if (segment1.getStartX() > segment2.getStartX()
                        && segment1.getFinishX() < segment2.getFinishX()) {
                    typeOfIntersection = TypeOfIntersection.OnePartOfOther;
                    startPointOfIntersection = segment1.getStart();
                    finishPointOfIntersection = segment1.getFinish();
                } else if (segment1.getFinishX() > segment2.getStartX()) {
                    typeOfIntersection = TypeOfIntersection.SegmentsHaveCommonPart;
                    startPointOfIntersection = segment2.getStart();
                    finishPointOfIntersection = segment1.getFinish();
                } else {
                    typeOfIntersection = TypeOfIntersection.SegmentsHaveCommonPart;
                    startPointOfIntersection = segment1.getStart();
                    finishPointOfIntersection = segment2.getFinish();
                }
            } else {
                double x = (b2 - b1) / (k1 - k2);
                double y = k1 * x + b1;
                typeOfIntersection = TypeOfIntersection.IntersectionInOnePoint;
                startPointOfIntersection = new Point(x, y);
                finishPointOfIntersection = new Point(x, y);
            }
        } else {
            typeOfIntersection = TypeOfIntersection.NotIntersection;
            startPointOfIntersection = null;
            finishPointOfIntersection = null;
        }
        return new Intersection(typeOfIntersection,
                new Segment(startPointOfIntersection, finishPointOfIntersection));
    }

    private boolean isIntersection(final Segment segment) {
        return vectorMultiplication(this.getStart(),
                this.getFinish(), segment.getStart())
                * vectorMultiplication(this.getStart(),
                this.getFinish(), segment.getFinish()) <= 0
                && vectorMultiplication(segment.getStart(),
                segment.getFinish(), this.getStart())
                * vectorMultiplication(segment.getStart(),
                segment.getFinish(), this.getFinish()) <= 0;
    }

    private double vectorMultiplication(final Point p1, final Point p2, final Point p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY())
                - (p1.getY() - p3.getY()) * (p2.getX() - p3.getX());
    }

    private double calculateSlope(final Segment segment) {
        if (segment.getStartY() == segment.getFinishY()) {
            return 0;
        }
        return (segment.getFinishY() - segment.getStartY())
                / (segment.getFinishX() - segment.getStartX());
    }

    private void makeFirstPointWithLessX(final Segment segment) {
        if (segment.getStartX() >= segment.getFinishX()) {
            changePoints(segment.getStart(), segment.getFinish());
        }
    }

    private void changePoints(final Point p1, final Point p2) {
        double tmpCoordinate = p1.getX();
        p1.setX(p2.getX());
        p2.setX(tmpCoordinate);
        tmpCoordinate = p1.getY();
        p1.setY(p2.getY());
        p2.setY(tmpCoordinate);
    }

    private double calculationLengthOfSegment() {
        if (start == null || finish == null) {
            return -1.0;
        }
        return Math.sqrt(Math.pow(start.getX() - finish.getX(), 2)
                + Math.pow(start.getY() - finish.getY(), 2));
    }
}
