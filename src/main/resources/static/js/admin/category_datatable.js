let table = new DataTable('#categoryTable');

$('#categoryTable').DataTable().destroy();

$(document).ready(function() {
    $('#categoryTable').DataTable({
        "language": {
            "sEmptyTable": "Không có dữ liệu",
            "sInfo": "Hiển thị từ _START_ đến _END_ (tổng: _TOTAL_) ",
            "lengthMenu": "Hiển thị _MENU_ danh mục",
            "emptyTable": "Không có dữ liệu phù hợp",
            "zeroRecords": "Không tìm thấy dữ liệu phù hợp",
            "infoEmpty": "0 / ",
            "infoFiltered": "_MAX_",
            "search": "Tìm kiếm",
            "paginate": {
                "previous": "Trước",
                "next": "Tiếp"
            },
        },
        lengthMenu: [5, 10, 15, 20],
        columnDefs: [
            { "orderable": false, "targets": [1,4,5,6] } // Thay [] bằng các chỉ mục của các cột muốn tắt sorting
        ],
        initComplete: function() {
            this.api()
                .columns()
                .every(function() {
                    var column = this;
                    var title = column.header().textContent.trim();
                    var footer = $(column.footer());

                    if (footer && footer.length > 0 && column.index() != 0 && column.index() != 1 && column.index() != 4 && column.index() !=5 && column.index() !=6) {
                        var input = document.createElement('input');
                        input.placeholder = 'Tìm kiếm ' + title;

                        $(input).appendTo(footer.empty())
                            .on('keyup change', function() {
                                if (column.search() !== this.value) {
                                    column.search(this.value).draw();
                                }
                            });
                    }
                });
        }
    });
});