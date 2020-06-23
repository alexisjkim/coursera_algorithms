package algorithms2.seam;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture pic;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();

        pic = new Picture(picture.width(), picture.height());

        for (int i = 0; i < pic.width(); i++) {
            for (int j = 0; j < pic.height(); j++) {
                pic.set(i,j,picture.get(i,j));
            }
        }
        // pic = picture;
    }

    // current picture
    public Picture picture() {
        return pic;
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) { // int[y][x] to find in pic
        if (x < 0) throw new IllegalArgumentException();
        if (x > width() - 1) throw new IllegalArgumentException();
        if (y < 0) throw new IllegalArgumentException();
        if (y > height() - 1) throw new IllegalArgumentException();

        if (y <= 0) return 1000;
        if (x <= 0) return 1000;
        if (y >= height() - 1) return 1000;
        if (x >= width() - 1) return 1000;

        Color left = pic.get(x, y-1);
        Color right = pic.get(x, y+1);
        Color top = pic.get(x-1, y);
        Color bottom = pic.get(x+1, y);
        double x_change = ((left.getRed() - right.getRed()) * (left.getRed() - right.getRed())) +
                ((left.getGreen() - right.getGreen())  * (left.getGreen() - right.getGreen())) +
                ((left.getBlue() - right.getBlue()) * (left.getBlue() - right.getBlue()) );
        double y_change = ((top.getRed() - bottom.getRed()) * (top.getRed() - bottom.getRed())) +
                ((top.getGreen() - bottom.getGreen()) * (top.getGreen() - bottom.getGreen())) +
                ((top.getBlue() - bottom.getBlue()) * (top.getBlue() - bottom.getBlue()));

        return Math.sqrt(x_change + y_change);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (pic.width() == 1) {
            int[] answer = new int[1];
            double min_energy = energy(0,0);
            for (int i = 0; i < pic.height(); i++) {
                if (energy(0,i) <= min_energy) {
                    answer[0] = i;
                }
            }
            return answer;
        }
        if (pic.height() == 1) {
            int[] answer = new int[pic.width()];
            for (int i = 0; i < pic.width();i++) answer[i] = 0;
            return answer;
        }

        int pic_height = pic.height();
        int pic_width = pic.width();
        double[][] energies = new double[pic_height][pic_width];
        double[][] shortest_path = new double[pic_height][pic_width];

        for (int i = 0; i < pic_height; i++) { // initializing left and right border
            energies[i][0] = 1000;
            energies[i][pic_width-1] = 1000;
            shortest_path[i][0] = 1000;
            shortest_path[i][pic_width-1] = 1000;
        }
        for (int i = 0; i < pic_width; i++) { // initializing top and bottom border
            energies[0][i] = 1000;
            energies[pic_height-1][i] = 1000;
            shortest_path[0][i] = 1000 * (i + 1);
            shortest_path[pic_height-1][i] = 1000 * (i + 1);
        }

        for (int i = 1; i < pic_height - 1; i++) { // adding values to the energy array
            for (int j = 1;j < pic_width - 1; j++) {
                energies[i][j] = energy(j, i);
            }
        }

        for (int i = 1; i < pic_width;  i++) { // calculating shortest path
            for (int j = 1; j < pic_height - 1; j++) {

                double energy1 = shortest_path[j-1][i-1];
                double energy2 = shortest_path[j][i-1];
                double energy3 = shortest_path[j+1][i-1];

                if (energy1 <= energy2 && energy1 <= energy3) {
                    shortest_path[j][i] = energy1 + energies[j][i];
                }
                if (energy2 <= energy1 && energy2 <= energy3) {
                    shortest_path[j][i] = energy2 + energies[j][i];
                }
                if (energy3 <= energy2 && energy3 <= energy1) {
                    shortest_path[j][i] = energy3 + energies[j][i];
                }
            }
        }
        int seam_end = 0; // index of bottom of seam
        for (int i = 1; i < pic_height; i++) { // finding which seam has the smallest
            if (shortest_path[i][pic_width-1] < shortest_path[seam_end][pic_width-1]) seam_end = i;
        }

        int[] answer = new int[pic_width]; // list that is returned
        int current_place = seam_end;
        answer[pic_width-1] = seam_end;
        for (int i = pic_width - 1; i > 0; i--) { // finding the path
            double energy1 = shortest_path[current_place - 1][i-1];
            double energy2 = shortest_path[current_place][i-1];
            double energy3 = shortest_path[current_place + 1][i-1];

            if (energy1 <= energy2 && energy1 <= energy3) {
                current_place--;
                answer[i-1] = current_place;
            }
            else if (energy2 <= energy1 && energy2 <= energy3) {
                answer[i-1] = current_place;
            }
            else {
                current_place++;
                answer[i-1] = current_place;
            }
        }
        return answer;
    }

    // sequence of indices for vertical seam

    public int[] findVerticalSeam() {
        if (pic.height() == 1) {
            int[] answer = new int[1];
            double min_energy = energy(0,0);
            for (int i = 0; i < pic.width(); i++) {
                if (energy(i,0) <= min_energy) {
                    answer[0] = i;
                }
            }
            return answer;
        }
        if (pic.width() == 1) {
            int[] answer = new int[pic.height()];
            for (int i = 0; i < pic.height();i++) answer[i] = 0;
            return answer;
        }

        int pic_height = pic.height();
        int pic_width = pic.width();
        double[][] energies = new double[pic_height][pic_width];
        double[][] shortest_path = new double[pic_height][pic_width];

        for (int i = 0; i < pic_width; i++) { // initializing top and bottom border
            energies[0][i] = 1000;
            energies[pic_height-1][i] = 1000;
            shortest_path[0][i] = 1000;
            shortest_path[pic_height-1][i] = 1000;
        }
        for (int i = 0; i < pic.height(); i++) { // initializing left and right border
            energies[i][0] = 1000;
            energies[i][pic_width-1] = 1000;
            shortest_path[i][0] = 1000 * (i + 1);
            shortest_path[i][pic_width-1] = 1000 * (i + 1);
        }

        for (int i = 1; i < pic.height() - 1; i++) { // adding values to the energy array
            for (int j = 1;j < pic.width() - 1; j++) {
                energies[i][j] = energy(j, i);
            }
        }

        for (int i = 1; i < pic.height(); i++) { // calculating shortest path
            for (int j = 1; j < pic.width() - 1; j++) {
                double energy1 = shortest_path[i-1][j-1];
                double energy2 = shortest_path[i-1][j];
                double energy3 = shortest_path[i-1][j+1];

                if (energy1 <= energy2 && energy1 <= energy3) {
                    shortest_path[i][j] = energy1 + energies[i][j];
                }
                if (energy2 <= energy1 && energy2 <= energy3) {
                    shortest_path[i][j] = energy2 + energies[i][j];
                }
                if (energy3 <= energy2 && energy3 <= energy1) {
                    shortest_path[i][j] = energy3 + energies[i][j];
                }
            }
        }
        int seam_end = 0; // index of bottom of seam
        for (int i = 1; i < pic_width; i++) { // finding which seam has the smallest
            if (shortest_path[pic_height-1][i] < shortest_path[pic_height-1][seam_end]) seam_end = i;
        }

        int[] answer = new int[pic_height]; // list that is returned
        int current_place = seam_end;
        answer[pic_height-1] = seam_end;
        for (int i = pic_height - 1; i > 0; i--) { // finding the path
            double energy1 = shortest_path[i-1][current_place - 1];
            double energy2 = shortest_path[i-1][current_place];
            double energy3 = shortest_path[i-1][current_place + 1];

            if (energy1 <= energy2 && energy1 <= energy3) {
                current_place--;
                answer[i-1] = current_place;
            }
            else if (energy3 <= energy2 && energy3 <= energy1) {
                current_place++;
                answer[i-1] = current_place;
            }
            else {
                answer[i-1] = current_place;
            }
        }
        return answer;
    }


    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        if (seam == null) throw new IllegalArgumentException();
        if (seam.length != pic.width()) throw new IllegalArgumentException();

        Picture new_pic = new Picture(pic.width(), pic.height() - 1);

        for (int i = 0; i < pic.width(); i++) {
            if (seam[i] < 0 || seam[i] > pic.height() - 1) throw new IllegalArgumentException();
            if (i > 0) {
                if (seam[i] - seam[i-1] != 0 && seam[i] - seam[i-1] != 1 && seam[i] - seam[i-1] != -1) throw new IllegalArgumentException();
            }
            int index = 0;
            for (int j = 0; j < pic.height() - 1; j++) {
                if (seam[i] != j) {
                    Color x = pic.get(i, index);
                    new_pic.set(i, j, x);
                    index++;
                }
                else {
                    index++;
                    new_pic.set(i, j, pic.get(i, index));
                    index++;
                }
            }
        }

        pic = new_pic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

        if (seam == null) throw new IllegalArgumentException();
        if (seam.length != pic.height()) throw new IllegalArgumentException();

        Picture new_pic = new Picture(pic.width() - 1, pic.height());

        for (int i = 0; i < pic.height(); i++) {
            if (seam[i] < 0 || seam[i] > pic.width() - 1) throw new IllegalArgumentException();
            if (i > 0) {
                if (seam[i] - seam[i-1] != 0 && seam[i] - seam[i-1] != 1 && seam[i] - seam[i-1] != -1) throw new IllegalArgumentException();
            }
            int index = 0;
            for (int j = 0; j < pic.width() - 1; j++) {

                if (seam[i] != j) {
                    new_pic.set(j, i, pic.get(index, i));
                    index ++;
                }
                else {
                    index++;
                    new_pic.set(j, i, pic.get(index, i));
                    index ++;

                }
            }
        }

        pic = new_pic;

    }

    //  unit testing (optional)
    public static void main(String[] args) {
        String file_name = "1x8.png";
        Picture picture = new Picture(file_name);
        SeamCarver carver = new SeamCarver(picture);
        int[] x = carver.findVerticalSeam();

        //     carver.removeVerticalSeam(carver.findVerticalSeam());

    }

}