
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Node {
    String photoPath;
    Node prev;
    Node next;

    public Node(String photoPath) {
        this.photoPath = photoPath;
        this.prev = null;
        this.next = null;
    }
}

class PhotoGallery {
    private Node head;
    private Node tail;
    private Node currentPhoto;
    private JFrame frame;
    private JLabel photoLabel;

    public PhotoGallery() {
        this.head = null;
        this.tail = null;
        this.currentPhoto = null;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Photo Gallery");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        photoLabel = new JLabel("Gallery is empty.", SwingConstants.CENTER);
        frame.add(photoLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton nextButton = new JButton("Next");
        JButton prevButton = new JButton("Previous");
        JButton deleteButton = new JButton("Delete");

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextPhoto();
            }
        });

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prevPhoto();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String photoPath = JOptionPane.showInputDialog("Enter photo path to delete:");
                if (deletePhoto(photoPath)) {
                    JOptionPane.showMessageDialog(frame, "Photo deleted.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Photo not found.");
                }
            }
        });

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void addPhoto(String photoPath) {
        Node newNode = new Node(photoPath);
        if (head == null) {
            head = newNode;
            tail = newNode;
            currentPhoto = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        updatePhotoDisplay();
    }

   

    public void nextPhoto() {
        if (currentPhoto != null && currentPhoto.next != null) {
            currentPhoto = currentPhoto.next;
            updatePhotoDisplay();
        } else if (currentPhoto != null) {
            JOptionPane.showMessageDialog(frame, "End of gallery.");
        } else {
            JOptionPane.showMessageDialog(frame, "Gallery is empty.");
        }
    }

    public void prevPhoto() {
        if (currentPhoto != null && currentPhoto.prev != null) {
            currentPhoto = currentPhoto.prev;
            updatePhotoDisplay();
        } else if (currentPhoto != null) {
            JOptionPane.showMessageDialog(frame, "Beginning of gallery.");
        } else {
            JOptionPane.showMessageDialog(frame, "Gallery is empty.");
        }
    }

    public boolean deletePhoto(String photoPath) {
        Node current = head;
        while (current != null) {
            if (current.photoPath.equals(photoPath)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next; // Update head 
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev; // Update tail 
                }

                if (currentPhoto == current) {
                    if (current.next != null) {
                        currentPhoto = current.next;
                    } else if (current.prev != null) {
                        currentPhoto = current.prev;
                    } else {
                        currentPhoto = null; // Gallery  empty
                    }
                }

                updatePhotoDisplay();
                return true; // Photo deleted
            }
            current = current.next;
        }
        return false; // Photo not found
    }

    private void updatePhotoDisplay() {
        displayCurrentPhoto();
    }

    public static void main(String[] args) {
        PhotoGallery gallery = new PhotoGallery();
        gallery.addPhoto("C:\\Users\\lanam\\OneDrive\\Desktop\\Java\\500\\photo1.jpg");
        gallery.addPhoto("C:\\Users\\lanam\\OneDrive\\Desktop\\Java\\500\\photo2.png");
        gallery.addPhoto("C:\\Users\\lanam\\OneDrive\\Desktop\\Java\\500\\photo3.jpg");
    }
    
    public void displayCurrentPhoto() {
        if (currentPhoto != null) {
            ImageIcon imageIcon = new ImageIcon(currentPhoto.photoPath);
            photoLabel.setIcon(imageIcon);
            photoLabel.setText(""); // Clear the text to show  image
        } else {
            photoLabel.setText("Gallery is empty.");
            photoLabel.setIcon(null); // Clear the icon if gallery is empty
        }
    }
}